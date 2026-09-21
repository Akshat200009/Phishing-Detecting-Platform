import Brand from '../components/Brand'

export default function WorkspaceLayout({ children, onSignOut }) {
  return <main className="min-h-screen bg-slate-950 px-6 py-8 text-slate-100 sm:px-10"><header className="mx-auto flex max-w-6xl items-center justify-between"><Brand /><button onClick={onSignOut} className="rounded-lg px-3 py-2 text-sm text-slate-400 hover:bg-slate-900 hover:text-white">Sign out</button></header>{children}</main>
}
