import { useState } from 'react'
import { api } from './api'

const features = [
  ['URL analysis', 'Inspect suspicious links before you open them.'],
  ['Email screening', 'Spot urgency, credential requests, and risky URLs.'],
  ['Clear results', 'See a simple risk score with the signals behind it.'],
]

function Brand() {
  return <div className="flex items-center gap-3">
    <div className="grid size-10 place-items-center rounded-xl bg-cyan-400 text-lg font-black text-slate-950 shadow-lg shadow-cyan-400/20">P</div>
    <span className="text-lg font-semibold tracking-tight text-white">PhishGuard</span>
  </div>
}

function AuthPage({ onAuthenticated }) {
  const [mode, setMode] = useState('login')
  const [form, setForm] = useState({ name: '', email: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const isLogin = mode === 'login'

  function updateField(event) {
    setForm({ ...form, [event.target.name]: event.target.value })
  }

  async function submit(event) {
    event.preventDefault()
    setError('')
    setLoading(true)
    try {
      if (isLogin) {
        const result = await api.login(form.email, form.password)
        localStorage.setItem('phishguard_token', result.token)
        onAuthenticated()
      } else {
        await api.register(form.name, form.email, form.password)
        setMode('login')
        setError('Account created. Sign in to continue.')
      }
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoading(false)
    }
  }

  return <main className="min-h-screen bg-slate-950 text-slate-100">
    <div className="mx-auto grid min-h-screen max-w-7xl lg:grid-cols-[1.1fr_.9fr]">
      <section className="relative overflow-hidden px-6 py-8 sm:px-10 lg:px-16 lg:py-12">
        <div className="absolute -left-20 top-28 size-80 rounded-full bg-cyan-400/10 blur-3xl" />
        <div className="relative flex h-full flex-col">
          <Brand />
          <div className="my-auto max-w-xl py-16 lg:py-0">
            <p className="mb-5 text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">Stay one step ahead</p>
            <h1 className="text-4xl font-semibold leading-tight tracking-tight text-white sm:text-5xl">Know what’s safe before it reaches you.</h1>
            <p className="mt-6 max-w-lg text-lg leading-8 text-slate-400">A focused workspace for checking suspicious emails and URLs, built around the signals that matter.</p>
            <div className="mt-10 grid gap-4 sm:grid-cols-3">
              {features.map(([title, text]) => <div key={title} className="rounded-2xl border border-slate-800 bg-slate-900/60 p-4">
                <div className="mb-3 size-2 rounded-full bg-cyan-400" />
                <h2 className="font-medium text-slate-100">{title}</h2>
                <p className="mt-1 text-sm leading-5 text-slate-400">{text}</p>
              </div>)}
            </div>
          </div>
          <p className="text-sm text-slate-500">PhishGuard helps you assess risk. Always use care with unexpected messages.</p>
        </div>
      </section>
      <section className="flex items-center bg-slate-900 px-6 py-10 sm:px-10 lg:px-16">
        <div className="mx-auto w-full max-w-md rounded-3xl border border-slate-800 bg-slate-950 p-7 shadow-2xl shadow-black/30 sm:p-9">
          <p className="text-sm font-medium text-cyan-300">{isLogin ? 'Welcome back' : 'Create your account'}</p>
          <h2 className="mt-2 text-3xl font-semibold tracking-tight text-white">{isLogin ? 'Sign in to PhishGuard' : 'Get started securely'}</h2>
          <p className="mt-3 text-sm leading-6 text-slate-400">{isLogin ? 'Use your account to access your scanning workspace.' : 'Your account keeps your scan history in one place.'}</p>
          <form className="mt-8 space-y-5" onSubmit={submit}>
            {!isLogin && <Field label="Full name" name="name" value={form.name} onChange={updateField} autoComplete="name" />}
            <Field label="Email address" name="email" type="email" value={form.email} onChange={updateField} autoComplete="email" />
            <Field label="Password" name="password" type="password" value={form.password} onChange={updateField} autoComplete={isLogin ? 'current-password' : 'new-password'} minLength={isLogin ? undefined : 8} />
            {error && <p className={`rounded-xl px-4 py-3 text-sm ${error.startsWith('Account created') ? 'bg-emerald-400/10 text-emerald-300' : 'bg-rose-400/10 text-rose-300'}`}>{error}</p>}
            <button disabled={loading} className="w-full rounded-xl bg-cyan-400 px-4 py-3 font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:cursor-not-allowed disabled:opacity-70">{loading ? 'Please wait…' : isLogin ? 'Sign in' : 'Create account'}</button>
          </form>
          <p className="mt-7 text-center text-sm text-slate-400">{isLogin ? 'New to PhishGuard?' : 'Already have an account?'} <button onClick={() => { setMode(isLogin ? 'register' : 'login'); setError('') }} className="font-semibold text-cyan-300 hover:text-cyan-200">{isLogin ? 'Create an account' : 'Sign in'}</button></p>
        </div>
      </section>
    </div>
  </main>
}

function Field({ label, ...props }) {
  return <label className="block text-sm font-medium text-slate-300">{label}
    <input required className="mt-2 block w-full rounded-xl border border-slate-700 bg-slate-900 px-4 py-3 text-slate-100 outline-none transition placeholder:text-slate-600 focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/20" {...props} />
  </label>
}

function Workspace({ onSignOut }) {
  return <main className="min-h-screen bg-slate-950 px-6 py-8 text-slate-100 sm:px-10">
    <header className="mx-auto flex max-w-6xl items-center justify-between"><Brand /><button onClick={onSignOut} className="rounded-lg px-3 py-2 text-sm text-slate-400 hover:bg-slate-900 hover:text-white">Sign out</button></header>
    <section className="mx-auto mt-20 max-w-3xl text-center"><p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">Your security workspace</p><h1 className="mt-4 text-4xl font-semibold tracking-tight">You’re signed in.</h1><p className="mt-4 text-slate-400">URL and email scan tools are coming next.</p></section>
  </main>
}

export default function App() {
  const [authenticated, setAuthenticated] = useState(() => Boolean(localStorage.getItem('phishguard_token')))
  return authenticated ? <Workspace onSignOut={() => { localStorage.removeItem('phishguard_token'); setAuthenticated(false) }} /> : <AuthPage onAuthenticated={() => setAuthenticated(true)} />
}
