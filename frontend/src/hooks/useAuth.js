import { useState } from 'react'

const TOKEN_KEY = 'phishguard_token'

export function useAuth() {
  const [authenticated, setAuthenticated] = useState(() => Boolean(localStorage.getItem(TOKEN_KEY)))
  const signIn = (token) => { localStorage.setItem(TOKEN_KEY, token); setAuthenticated(true) }
  const signOut = () => { localStorage.removeItem(TOKEN_KEY); setAuthenticated(false) }
  return { authenticated, signIn, signOut }
}
