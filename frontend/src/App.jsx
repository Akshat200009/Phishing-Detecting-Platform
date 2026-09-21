import { useAuth } from './hooks/useAuth'
import AuthPage from './pages/AuthPage'
import UrlScannerPage from './pages/UrlScannerPage'

export default function App() {
  const { authenticated, signIn, signOut } = useAuth()
  return authenticated ? <UrlScannerPage onSignOut={signOut} /> : <AuthPage onAuthenticated={signIn} />
}
/*
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
  const [url, setUrl] = useState('')
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function scanUrl(event) {
    event.preventDefault()
    setError('')
    setResult(null)
    setLoading(true)
    try {
      setResult(await api.scanUrl(url))
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoading(false)
    }
  }

  return <main className="min-h-screen bg-slate-950 px-6 py-8 text-slate-100 sm:px-10">
    <header className="mx-auto flex max-w-6xl items-center justify-between"><Brand /><button onClick={onSignOut} className="rounded-lg px-3 py-2 text-sm text-slate-400 hover:bg-slate-900 hover:text-white">Sign out</button></header>
    <section className="mx-auto grid max-w-6xl gap-10 py-14 lg:grid-cols-[1fr_.8fr] lg:py-20">
      <div className="max-w-xl"><p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-300">URL scanner</p><h1 className="mt-4 text-4xl font-semibold tracking-tight sm:text-5xl">Check a link before you trust it.</h1><p className="mt-5 text-lg leading-8 text-slate-400">Paste a full URL to check its security signals, reputation, and risk score.</p>
        <form className="mt-9" onSubmit={scanUrl}><label className="sr-only" htmlFor="url">Website address</label><div className="rounded-2xl border border-slate-700 bg-slate-900 p-2 shadow-xl shadow-black/10 sm:flex sm:gap-2"><input id="url" required type="url" value={url} onChange={(event) => setUrl(event.target.value)} placeholder="https://example.com" className="w-full bg-transparent px-4 py-3 text-slate-100 outline-none placeholder:text-slate-500" /><button disabled={loading} className="mt-2 w-full rounded-xl bg-cyan-400 px-5 py-3 font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:opacity-70 sm:mt-0 sm:w-auto">{loading ? 'Scanning…' : 'Scan URL'}</button></div></form>
        {error && <p className="mt-4 rounded-xl bg-rose-400/10 px-4 py-3 text-sm text-rose-300">{error}</p>}
        <div className="mt-10 flex gap-6 text-sm text-slate-500"><span>• HTTPS check</span><span>• Domain signals</span><span>• Threat lookups</span></div>
      </div>
      <ScanResult result={result} />
    </section>
  </main>
}

function ScanResult({ result }) {
  if (!result) return <aside className="flex min-h-80 items-center rounded-3xl border border-dashed border-slate-700 bg-slate-900/40 p-8 text-center"><div className="w-full"><div className="mx-auto grid size-14 place-items-center rounded-2xl bg-slate-800 text-2xl text-cyan-300">⌁</div><h2 className="mt-5 text-lg font-semibold">Your result will appear here</h2><p className="mt-2 text-sm leading-6 text-slate-400">We’ll translate the scan signals into a clear risk assessment.</p></div></aside>
  const level = result.riskScore >= 70 ? 'High risk' : result.riskScore >= 35 ? 'Needs caution' : 'Low risk'
  const scoreStyle = result.riskScore >= 70
    ? 'border-rose-400/20 bg-rose-400/10 text-rose-300'
    : result.riskScore >= 35
      ? 'border-amber-400/20 bg-amber-400/10 text-amber-300'
      : 'border-emerald-400/20 bg-emerald-400/10 text-emerald-300'
  const signals = [
    ['HTTPS connection', result.https],
    ['Suspicious keywords', result.suspiciousKeyword],
    ['Suspicious domain', result.suspiciousDomain],
    ['IP address URL', result.ipAddress],
  ]
  return <aside className="rounded-3xl border border-slate-800 bg-slate-900 p-7 shadow-2xl shadow-black/20"><div className="flex items-start justify-between gap-5"><div><p className="text-sm font-medium text-slate-400">Scan assessment</p><h2 className="mt-1 text-2xl font-semibold text-white">{level}</h2></div><div className={`grid size-20 place-items-center rounded-full border-8 text-xl font-bold ${scoreStyle}`}>{result.riskScore}<span className="-ml-4 mt-7 text-xs font-medium">/100</span></div></div><p className="mt-6 break-all rounded-xl bg-slate-950 px-4 py-3 text-sm text-slate-300">{result.url}</p><p className="mt-3 text-sm text-slate-400">Status: <span className="font-medium text-slate-200">{result.status}</span></p><div className="mt-7 space-y-3 border-t border-slate-800 pt-6">{signals.map(([label, value]) => <div key={label} className="flex items-center justify-between text-sm"><span className="text-slate-400">{label}</span><span className={value ? (label === 'HTTPS connection' ? 'text-emerald-300' : 'text-rose-300') : (label === 'HTTPS connection' ? 'text-rose-300' : 'text-slate-500')}>{value ? (label === 'HTTPS connection' ? 'Detected' : 'Flagged') : (label === 'HTTPS connection' ? 'Not detected' : 'Clear')}</span></div>)}</div>{result.resolvedIp && <p className="mt-5 text-xs text-slate-500">Resolved IP: {result.resolvedIp}</p>}</aside>
}

export default function App() {
  const [authenticated, setAuthenticated] = useState(() => Boolean(localStorage.getItem('phishguard_token')))
  return authenticated ? <Workspace onSignOut={() => { localStorage.removeItem('phishguard_token'); setAuthenticated(false) }} /> : <AuthPage onAuthenticated={() => setAuthenticated(true)} />
}
*/
