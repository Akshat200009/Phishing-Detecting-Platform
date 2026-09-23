import { useState } from 'react'
import Brand from '../components/Brand'
import FormField from '../components/FormField'
import { api } from '../services/api'

const features = [['URL analysis', 'Inspect suspicious links before you open them.'], ['Email screening', 'Spot urgency, credential requests, and risky URLs.'], ['Clear results', 'See a simple risk score with the signals behind it.']]

export default function PortalAuthPage({ onAuthenticated }) {
  const [mode, setMode] = useState('login')
  const [form, setForm] = useState({ name: '', email: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const isRegister = mode === 'register'
  const isAdmin = mode === 'admin'
  const updateField = (event) => setForm({ ...form, [event.target.name]: event.target.value })

  async function submit(event) {
    event.preventDefault()
    setError('')
    setLoading(true)
    try {
      if (isRegister) {
        await api.register(form.name, form.email, form.password)
        setMode('login')
        setError('Account created. Sign in to continue.')
        return
      }
      const result = await api.login(form.email, form.password)
      if (isAdmin && result.role !== 'ADMIN') throw new Error('This account does not have administrator access.')
      onAuthenticated(result)
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoading(false)
    }
  }

  const title = isRegister ? 'Create your account' : isAdmin ? 'Administrator sign in' : 'Sign in to PhishGuard'
  const description = isRegister ? 'Your account keeps your scan history in one place.' : isAdmin ? 'Use an administrator account to review platform activity and manage users.' : 'Use your account to access your scanning workspace.'

  return <main className="min-h-screen bg-slate-950 text-slate-100"><div className="mx-auto grid min-h-screen max-w-7xl lg:grid-cols-[1.1fr_.9fr]"><section className="relative overflow-hidden px-6 py-8 sm:px-10 lg:px-16 lg:py-12"><div className="absolute -left-20 top-28 size-80 rounded-full bg-cyan-400/10 blur-3xl" /><div className="relative flex h-full flex-col"><Brand /><div className="my-auto max-w-xl py-16 lg:py-0"><p className="mb-5 text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">Stay one step ahead</p><h1 className="text-4xl font-semibold leading-tight tracking-tight text-white sm:text-5xl">Know what's safe before it reaches you.</h1><p className="mt-6 max-w-lg text-lg leading-8 text-slate-400">A focused workspace for checking suspicious emails and URLs, built around the signals that matter.</p><div className="mt-10 grid gap-4 sm:grid-cols-3">{features.map(([featureTitle, text]) => <div key={featureTitle} className="rounded-2xl border border-slate-800 bg-slate-900/60 p-4"><div className="mb-3 size-2 rounded-full bg-cyan-400" /><h2 className="font-medium text-slate-100">{featureTitle}</h2><p className="mt-1 text-sm leading-5 text-slate-400">{text}</p></div>)}</div></div><p className="text-sm text-slate-500">PhishGuard helps you assess risk. Always use care with unexpected messages.</p></div></section><section className="flex items-center bg-slate-900 px-6 py-10 sm:px-10 lg:px-16"><div className="mx-auto w-full max-w-md rounded-3xl border border-slate-800 bg-slate-950 p-7 shadow-2xl shadow-black/30 sm:p-9"><div className="flex rounded-xl bg-slate-900 p-1 text-sm"><button onClick={() => { setMode('login'); setError('') }} className={`flex-1 rounded-lg px-3 py-2 ${mode === 'login' ? 'bg-slate-800 text-white' : 'text-slate-400'}`}>Sign in</button><button onClick={() => { setMode('register'); setError('') }} className={`flex-1 rounded-lg px-3 py-2 ${mode === 'register' ? 'bg-slate-800 text-white' : 'text-slate-400'}`}>Register</button><button onClick={() => { setMode('admin'); setError('') }} className={`flex-1 rounded-lg px-3 py-2 ${mode === 'admin' ? 'bg-slate-800 text-white' : 'text-slate-400'}`}>Admin</button></div><p className="mt-7 text-sm font-medium text-cyan-300">{isAdmin ? 'Restricted access' : isRegister ? 'Get started' : 'Welcome back'}</p><h2 className="mt-2 text-3xl font-semibold tracking-tight text-white">{title}</h2><p className="mt-3 text-sm leading-6 text-slate-400">{description}</p><form className="mt-8 space-y-5" onSubmit={submit}>{isRegister && <FormField label="Full name" name="name" value={form.name} onChange={updateField} autoComplete="name" />}<FormField label="Email address" name="email" type="email" value={form.email} onChange={updateField} autoComplete="email" /><FormField label="Password" name="password" type="password" value={form.password} onChange={updateField} autoComplete={isRegister ? 'new-password' : 'current-password'} minLength={isRegister ? 8 : undefined} />{error && <p className={`rounded-xl px-4 py-3 text-sm ${error.startsWith('Account created') ? 'bg-emerald-400/10 text-emerald-300' : 'bg-rose-400/10 text-rose-300'}`}>{error}</p>}<button disabled={loading} className="w-full rounded-xl bg-cyan-400 px-4 py-3 font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:cursor-not-allowed disabled:opacity-70">{loading ? 'Please wait...' : isRegister ? 'Create account' : isAdmin ? 'Sign in as administrator' : 'Sign in'}</button></form>{isAdmin && <p className="mt-5 text-center text-xs leading-5 text-slate-500">Administrator accounts are created securely by server configuration, not through public registration.</p>}</div></section></div></main>
}
