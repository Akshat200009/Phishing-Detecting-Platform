import { useState } from 'react'
import ScanResult from '../components/ScanResult'
import WorkspaceLayout from '../layouts/WorkspaceLayout'
import { api } from '../services/api'

export default function UrlScannerPage({ onSignOut }) {
  const [url, setUrl] = useState('')
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function scanUrl(event) {
    event.preventDefault()
    setError('')
    setResult(null)
    setLoading(true)
    try { setResult(await api.scanUrl(url)) } catch (requestError) { setError(requestError.message) } finally { setLoading(false) }
  }

  return <WorkspaceLayout onSignOut={onSignOut}><section className="mx-auto grid max-w-6xl gap-10 py-14 lg:grid-cols-[1fr_.8fr] lg:py-20"><div className="max-w-xl"><p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">URL scanner</p><h1 className="mt-4 text-4xl font-semibold tracking-tight sm:text-5xl">Check a link before you trust it.</h1><p className="mt-5 text-lg leading-8 text-slate-400">Paste a full URL to check its security signals, reputation, and risk score.</p><form className="mt-9" onSubmit={scanUrl}><label className="sr-only" htmlFor="url">Website address</label><div className="rounded-2xl border border-slate-700 bg-slate-900 p-2 shadow-xl shadow-black/10 sm:flex sm:gap-2"><input id="url" required type="url" value={url} onChange={(event) => setUrl(event.target.value)} placeholder="https://example.com" className="w-full bg-transparent px-4 py-3 text-slate-100 outline-none placeholder:text-slate-500" /><button disabled={loading} className="mt-2 w-full rounded-xl bg-cyan-400 px-5 py-3 font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:opacity-70 sm:mt-0 sm:w-auto">{loading ? 'Scanning...' : 'Scan URL'}</button></div></form>{error && <p className="mt-4 rounded-xl bg-rose-400/10 px-4 py-3 text-sm text-rose-300">{error}</p>}<div className="mt-10 flex gap-6 text-sm text-slate-500"><span>- HTTPS check</span><span>- Domain signals</span><span>- Threat lookups</span></div></div><ScanResult result={result} /></section></WorkspaceLayout>
}
