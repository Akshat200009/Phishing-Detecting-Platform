import { useState } from 'react'
import { useAuth } from './hooks/useAuth'
import PortalAuthPage from './pages/PortalAuthPage'
import AdminDashboardPage from './pages/AdminDashboardPage'
import DashboardPage from './pages/DashboardPage'
import EmailScannerPage from './pages/EmailScannerPage'
import UrlScannerPage from './pages/UrlScannerPage'

export default function App() {
  const { authenticated, role, signIn, signOut } = useAuth()
  const [tool, setTool] = useState('url')

  if (!authenticated) return <PortalAuthPage onAuthenticated={signIn} />

  if (role === 'ADMIN') return <AdminDashboardPage onSignOut={signOut} />

  const scannerProps = { activeTool: tool, onNavigate: setTool, onSignOut: signOut }
  if (tool === 'dashboard') return <DashboardPage {...scannerProps} />
  return tool === 'email' ? <EmailScannerPage {...scannerProps} /> : <UrlScannerPage {...scannerProps} />
}

