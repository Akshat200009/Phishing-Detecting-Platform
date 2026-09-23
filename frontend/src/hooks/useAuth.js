import { useState } from 'react'

const TOKEN_KEY = 'phishguard_token'
const ROLE_KEY = 'phishguard_role'

export function useAuth() {
  const [authenticated, setAuthenticated] = useState(() => Boolean(localStorage.getItem(TOKEN_KEY)))
  const [role, setRole] = useState(() => localStorage.getItem(ROLE_KEY) ?? 'USER')
  const signIn = ({ token, role: nextRole }) => { localStorage.setItem(TOKEN_KEY, token); localStorage.setItem(ROLE_KEY, nextRole); setRole(nextRole); setAuthenticated(true) }
  const signOut = () => { localStorage.removeItem(TOKEN_KEY); localStorage.removeItem(ROLE_KEY); setRole('USER'); setAuthenticated(false) }
  return { authenticated, role, signIn, signOut }
}
