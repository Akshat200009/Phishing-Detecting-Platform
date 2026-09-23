import { useEffect, useState } from 'react'
import Brand from '../components/Brand'
import { api } from '../services/api'

const blankStats = { totalScans: 0, safeScans: 0, suspiciousScans: 0, maliciousScans: 0, averageRiskScore: 0 }

function formatDate(value) {
  return value ? new Intl.DateTimeFormat(undefined, { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(value)) : 'Unknown date'
}

function Metric({ label, value, tone = 'text-white' }) {
  return <article className="rounded-2xl border border-slate-800 bg-slate-900 p-5"><p className="text-sm text-slate-400">{label}</p><p className={`mt-2 text-3xl font-semibold ${tone}`}>{value}</p></article>
}

export default function AdminDashboardPage({ onSignOut }) {
  const [statistics, setStatistics] = useState(blankStats)
  const [threats, setThreats] = useState(blankStats)
  const [scans, setScans] = useState([])
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [updatingId, setUpdatingId] = useState(null)

  async function loadAdminData() {
    setLoading(true)
    setError('')
    try {
      const [nextStatistics, nextThreats, nextScans, nextUsers] = await Promise.all([api.getAdminStatistics(), api.getAdminThreatAnalytics(), api.getAdminScans(), api.getAdminUsers()])
      setStatistics(nextStatistics)
      setThreats(nextThreats)
      setScans(nextScans)
      setUsers(nextUsers)
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { loadAdminData() }, [])

  async function updateRole(id, role) {
    setUpdatingId(id)
    setError('')
    try {
      const user = await api.updateUserRole(id, role)
      setUsers(users.map((item) => item.id === id ? user : item))
    } catch (requestError) {
      setError(requestError.message)
    } finally { setUpdatingId(null) }
  }

  async function updateStatus(id, active) {
    setUpdatingId(id)
    setError('')
    try {
      const user = await api.updateUserStatus(id, active)
      setUsers(users.map((item) => item.id === id ? user : item))
    } catch (requestError) {
      setError(requestError.message)
    } finally { setUpdatingId(null) }
  }

  return <main className="min-h-screen bg-slate-950 px-4 py-5 text-slate-100 sm:px-8 sm:py-8"><header className="mx-auto flex max-w-7xl flex-col gap-4 sm:flex-row sm:items-center sm:justify-between"><Brand /><div className="flex items-center gap-3"><span className="rounded-full bg-cyan-400/10 px-3 py-1.5 text-sm font-medium text-cyan-300">Administrator</span><button onClick={onSignOut} className="rounded-lg px-3 py-2 text-sm text-slate-400 hover:bg-slate-900 hover:text-white">Sign out</button></div></header><section className="mx-auto max-w-7xl py-10 sm:py-14"><div className="flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between"><div><p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">Platform oversight</p><h1 className="mt-3 text-3xl font-semibold tracking-tight text-white sm:text-4xl">Admin dashboard</h1><p className="mt-3 max-w-2xl text-slate-400">Monitor all completed email and URL scans, identify threats, and manage account access.</p></div><button onClick={loadAdminData} disabled={loading} className="rounded-xl bg-cyan-400 px-4 py-3 text-sm font-semibold text-slate-950 hover:bg-cyan-300 disabled:opacity-60">{loading ? 'Loading...' : 'Refresh data'}</button></div>{error && <div className="mt-7 flex flex-wrap items-center justify-between gap-3 rounded-2xl bg-rose-400/10 px-4 py-3 text-sm text-rose-300"><span>{error}</span><button onClick={loadAdminData} className="font-semibold hover:text-rose-200">Try again</button></div>}<div className="mt-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-5"><Metric label="Total scans" value={statistics.totalScans} /><Metric label="Average risk" value={`${Math.round(statistics.averageRiskScore)}/100`} /><Metric label="Safe" value={threats.safeScans} tone="text-emerald-300" /><Metric label="Suspicious" value={threats.suspiciousScans} tone="text-amber-300" /><Metric label="Malicious" value={threats.maliciousScans} tone="text-rose-300" /></div><section className="mt-10 rounded-3xl border border-slate-800 bg-slate-900 p-5 sm:p-7"><h2 className="text-xl font-semibold text-white">Platform scans</h2><p className="mt-1 text-sm text-slate-400">All completed URL and email assessments, newest first.</p><div className="mt-6 overflow-x-auto"><table className="w-full min-w-[650px] text-left text-sm"><thead className="border-b border-slate-800 text-slate-500"><tr><th className="px-3 py-3 font-medium">Target</th><th className="px-3 py-3 font-medium">Type</th><th className="px-3 py-3 font-medium">Risk</th><th className="px-3 py-3 font-medium">Status</th><th className="px-3 py-3 font-medium">Scanned</th></tr></thead><tbody>{loading ? <tr><td colSpan="5" className="px-3 py-10 text-center text-slate-400">Loading scans...</td></tr> : scans.length ? scans.map((scan) => <tr key={`${scan.type}-${scan.id}`} className="border-b border-slate-800/80 last:border-0"><td className="max-w-sm break-all px-3 py-4 text-slate-200">{scan.target}</td><td className="px-3 py-4 text-slate-400">{scan.type}</td><td className="px-3 py-4 text-slate-200">{scan.riskScore}/100</td><td className={`px-3 py-4 font-medium ${scan.status === 'MALICIOUS' ? 'text-rose-300' : scan.status === 'SUSPICIOUS' ? 'text-amber-300' : 'text-emerald-300'}`}>{scan.status}</td><td className="whitespace-nowrap px-3 py-4 text-slate-400">{formatDate(scan.scannedAt)}</td></tr>) : <tr><td colSpan="5" className="px-3 py-10 text-center text-slate-400">No scans have been recorded.</td></tr>}</tbody></table></div></section><section className="mt-10 rounded-3xl border border-slate-800 bg-slate-900 p-5 sm:p-7"><h2 className="text-xl font-semibold text-white">User management</h2><p className="mt-1 text-sm text-slate-400">Change roles or activate and deactivate accounts. Server safeguards protect your own account and the last active admin.</p><div className="mt-6 overflow-x-auto"><table className="w-full min-w-[760px] text-left text-sm"><thead className="border-b border-slate-800 text-slate-500"><tr><th className="px-3 py-3 font-medium">User</th><th className="px-3 py-3 font-medium">Email</th><th className="px-3 py-3 font-medium">Role</th><th className="px-3 py-3 font-medium">Status</th><th className="px-3 py-3 font-medium">Action</th></tr></thead><tbody>{loading ? <tr><td colSpan="5" className="px-3 py-10 text-center text-slate-400">Loading users...</td></tr> : users.map((user) => <tr key={user.id} className="border-b border-slate-800/80 last:border-0"><td className="px-3 py-4 text-slate-200">{user.name}</td><td className="px-3 py-4 text-slate-400">{user.email}</td><td className="px-3 py-4"><select value={user.role} disabled={updatingId === user.id} onChange={(event) => updateRole(user.id, event.target.value)} className="rounded-lg border border-slate-700 bg-slate-950 px-3 py-2 text-slate-200 outline-none focus:border-cyan-400"><option value="USER">User</option><option value="ADMIN">Admin</option></select></td><td className={`px-3 py-4 font-medium ${user.active ? 'text-emerald-300' : 'text-slate-500'}`}>{user.active ? 'Active' : 'Inactive'}</td><td className="px-3 py-4"><button disabled={updatingId === user.id} onClick={() => updateStatus(user.id, !user.active)} className="rounded-lg px-3 py-2 text-sm text-cyan-300 hover:bg-slate-800 disabled:opacity-60">{user.active ? 'Deactivate' : 'Activate'}</button></td></tr>)}</tbody></table></div></section></section></main>
}
