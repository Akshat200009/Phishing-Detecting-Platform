import { useState } from 'react'
import EmailScanResult from '../components/EmailScanResult'
import WorkspaceLayout from '../layouts/WorkspaceLayout'
import { api } from '../services/api'

const emptyEmail = { sender: '', subject: '', body: '' }

export default function EmailScannerPage({ activeTool, onNavigate, onSignOut }) {
  const [email, setEmail] = useState(emptyEmail)
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  function updateField(event) {
    setEmail({ ...email, [event.target.name]: event.target.value })
  }

  async function scanEmail(event) {
    event.preventDefault()
    setError('')
    setResult(null)
    setLoading(true)
    try { setResult(await api.scanEmail(email)) } catch (requestError) { setError(requestError.message) } finally { setLoading(false) }
  }

  return <WorkspaceLayout activeTool={activeTool} onNavigate={onNavigate} onSignOut={onSignOut}><section className="mx-auto grid max-w-6xl gap-10 py-14 lg:grid-cols-[1fr_.8fr] lg:py-20"><div className="max-w-xl"><p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">Email scanner</p><h1 className="mt-4 text-4xl font-semibold tracking-tight sm:text-5xl">Read the warning signs before you reply.</h1><p className="mt-5 text-lg leading-8 text-slate-400">Paste an email's details to check for risky language, credential requests, and suspicious links.</p><form className="mt-9 space-y-5" onSubmit={scanEmail}><label className="block text-sm font-medium text-slate-300">Sender<input required name="sender" type="email" value={email.sender} onChange={updateField} placeholder="sender@example.com" className="mt-2 block w-full rounded-xl border border-slate-700 bg-slate-900 px-4 py-3 text-slate-100 outline-none placeholder:text-slate-600 focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/20" /></label><label className="block text-sm font-medium text-slate-300">Subject<input required name="subject" value={email.subject} onChange={updateField} placeholder="Action required: verify your account" className="mt-2 block w-full rounded-xl border border-slate-700 bg-slate-900 px-4 py-3 text-slate-100 outline-none placeholder:text-slate-600 focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/20" /></label><label className="block text-sm font-medium text-slate-300">Email body<textarea required name="body" rows="8" value={email.body} onChange={updateField} placeholder="Paste the message content here..." className="mt-2 block w-full resize-y rounded-xl border border-slate-700 bg-slate-900 px-4 py-3 text-slate-100 outline-none placeholder:text-slate-600 focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/20" /></label><button disabled={loading} className="w-full rounded-xl bg-cyan-400 px-5 py-3 font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:cursor-not-allowed disabled:opacity-70">{loading ? 'Analyzing email...' : 'Analyze email'}</button></form>{error && <p className="mt-4 rounded-xl bg-rose-400/10 px-4 py-3 text-sm text-rose-300">{error}</p>}</div><EmailScanResult result={result} /></section></WorkspaceLayout>
}
