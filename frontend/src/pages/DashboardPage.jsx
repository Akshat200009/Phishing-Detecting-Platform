import { useEffect, useState } from 'react'
import WorkspaceLayout from '../layouts/WorkspaceLayout'
import { api } from '../services/api'
import { getRiskLevel } from '../utils/risk'

const emptyReport = { totalScans: 0, urlScans: 0, emailScans: 0, safe: 0, suspicious: 0, malicious: 0, averageRiskScore: 0 }
const initialFilters = { search: '', type: '', status: '' }

function formatDate(value) {
  return value ? new Intl.DateTimeFormat(undefined, { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(value)) : 'Unknown date'
}

function StatCard({ label, value, detail, tone = 'text-white' }) {
  return <article className="rounded-2xl border border-slate-800 bg-slate-900 p-5"><p className="text-sm text-slate-400">{label}</p><p className={`mt-2 text-3xl font-semibold ${tone}`}>{value}</p><p className="mt-1 text-sm text-slate-500">{detail}</p></article>
}

export default function DashboardPage({ activeTool, onNavigate, onSignOut }) {
  const [report, setReport] = useState(emptyReport)
  const [history, setHistory] = useState([])
  const [filters, setFilters] = useState(initialFilters)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  async function loadDashboard(nextFilters = filters) {
    setLoading(true)
    setError('')
    try {
      const [nextReport, nextHistory] = await Promise.all([api.getScanReport(), api.getScanHistory(nextFilters)])
      setReport(nextReport)
      setHistory(nextHistory)
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { loadDashboard(initialFilters) }, [])

  function updateFilter(event) {
    setFilters({ ...filters, [event.target.name]: event.target.value })
  }

  function applyFilters(event) {
    event.preventDefault()
    loadDashboard(filters)
  }

  function clearFilters() {
    setFilters(initialFilters)
    loadDashboard(initialFilters)
  }

  return <WorkspaceLayout activeTool={activeTool} onNavigate={onNavigate} onSignOut={onSignOut}><section className="mx-auto max-w-6xl py-10 sm:py-14"><div className="max-w-2xl"><p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">Your dashboard</p><h1 className="mt-3 text-3xl font-semibold tracking-tight text-white sm:text-4xl">See your scan history at a glance.</h1><p className="mt-4 text-lg leading-8 text-slate-400">Review every URL and email assessment, then focus on the scans that need attention.</p></div>{error && <div className="mt-7 flex flex-wrap items-center justify-between gap-3 rounded-2xl bg-rose-400/10 px-4 py-3 text-sm text-rose-300"><span>{error}</span><button onClick={() => loadDashboard()} className="font-semibold hover:text-rose-200">Try again</button></div>}<div className="mt-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-4"><StatCard label="Total scans" value={report.totalScans} detail={`${report.urlScans} URLs · ${report.emailScans} emails`} /><StatCard label="Average risk" value={`${Math.round(report.averageRiskScore)}/100`} detail="Across all your scans" /><StatCard label="Safe" value={report.safe} detail="No immediate danger found" tone="text-emerald-300" /><StatCard label="Needs attention" value={report.suspicious + report.malicious} detail={`${report.suspicious} suspicious · ${report.malicious} malicious`} tone="text-rose-300" /></div><section className="mt-10 rounded-3xl border border-slate-800 bg-slate-900 p-5 sm:p-7"><div className="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between"><div><h2 className="text-xl font-semibold text-white">Scan history</h2><p className="mt-1 text-sm text-slate-400">Filter your completed checks by target, type, or risk status.</p></div><button onClick={() => loadDashboard()} disabled={loading} className="rounded-lg px-3 py-2 text-sm text-cyan-300 hover:bg-slate-800 disabled:opacity-60">Refresh</button></div><form onSubmit={applyFilters} className="mt-6 grid gap-3 md:grid-cols-[1fr_auto_auto_auto]"><input name="search" value={filters.search} onChange={updateFilter} placeholder="Search URL, sender, or subject" className="min-w-0 rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-slate-100 outline-none placeholder:text-slate-600 focus:border-cyan-400" /><select name="type" value={filters.type} onChange={updateFilter} className="rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-slate-100 outline-none focus:border-cyan-400"><option value="">All types</option><option value="URL">URLs</option><option value="EMAIL">Emails</option></select><select name="status" value={filters.status} onChange={updateFilter} className="rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-slate-100 outline-none focus:border-cyan-400"><option value="">All statuses</option><option value="SAFE">Safe</option><option value="SUSPICIOUS">Suspicious</option><option value="MALICIOUS">Malicious</option></select><div className="flex gap-2"><button className="flex-1 rounded-xl bg-cyan-400 px-4 py-3 text-sm font-semibold text-slate-950 hover:bg-cyan-300">Apply</button><button type="button" onClick={clearFilters} className="rounded-xl px-3 py-3 text-sm text-slate-400 hover:bg-slate-800 hover:text-white">Clear</button></div></form><div className="mt-7 overflow-x-auto"><table className="w-full min-w-[600px] text-left text-sm"><thead className="border-b border-slate-800 text-slate-500"><tr><th className="px-3 py-3 font-medium">Target</th><th className="px-3 py-3 font-medium">Type</th><th className="px-3 py-3 font-medium">Risk</th><th className="px-3 py-3 font-medium">Status</th><th className="px-3 py-3 font-medium">Scanned</th></tr></thead><tbody>{loading ? <tr><td colSpan="5" className="px-3 py-10 text-center text-slate-400">Loading your scan history...</td></tr> : history.length ? history.map((scan) => { const risk = getRiskLevel(scan.riskScore); return <tr key={`${scan.type}-${scan.id}`} className="border-b border-slate-800/80 last:border-0"><td className="max-w-xs break-all px-3 py-4 text-slate-200">{scan.target}</td><td className="px-3 py-4 text-slate-400">{scan.type === 'EMAIL' ? 'Email' : 'URL'}</td><td className="px-3 py-4 font-medium text-slate-200">{scan.riskScore}/100</td><td className={`px-3 py-4 font-medium ${risk.style.split(' ').at(-1)}`}>{scan.status}</td><td className="whitespace-nowrap px-3 py-4 text-slate-400">{formatDate(scan.scannedAt)}</td></tr> }) : <tr><td colSpan="5" className="px-3 py-10 text-center text-slate-400">No scans match these filters. Run a URL or email scan to see it here.</td></tr>}</tbody></table></div></section></section></WorkspaceLayout>
}
