import Brand from '../components/Brand'

export default function WorkspaceLayout({ activeTool, children, onNavigate, onSignOut }) {
  const tools = [['url', 'URL scan'], ['email', 'Email scan'], ['dashboard', 'Dashboard']]
  return <main className="min-h-screen bg-slate-950 px-4 py-5 text-slate-100 sm:px-8 sm:py-8"><header className="mx-auto flex max-w-6xl flex-col gap-4 sm:flex-row sm:items-center sm:justify-between"><Brand /><div className="flex flex-wrap items-center gap-2"><nav aria-label="Workspace tools" className="flex flex-wrap rounded-xl bg-slate-900 p-1">{tools.map(([tool, label]) => <button key={tool} onClick={() => onNavigate(tool)} className={`rounded-lg px-3 py-2 text-sm transition ${activeTool === tool ? 'bg-slate-800 text-white' : 'text-slate-400 hover:text-white'}`}>{label}</button>)}</nav><button onClick={onSignOut} className="rounded-lg px-3 py-2 text-sm text-slate-400 hover:bg-slate-900 hover:text-white">Sign out</button></div></header>{children}</main>
}
