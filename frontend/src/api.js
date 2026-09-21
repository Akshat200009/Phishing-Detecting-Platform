const API_BASE_URL = import.meta.env.VITE_API_URL ?? ''

async function request(path, options = {}) {
  const token = localStorage.getItem('phishguard_token')
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...options.headers,
    },
  })

  if (!response.ok) {
    const error = await response.text()
    throw new Error(error || 'Something went wrong. Please try again.')
  }

  return response.status === 204 ? null : response.json()
}

export const api = {
  login: (email, password) => request('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email, password }),
  }),
  register: (name, email, password) => request('/api/auth/register', {
    method: 'POST',
    body: JSON.stringify({ name, email, password }),
  }),
  logout: () => request('/api/auth/logout', { method: 'POST' }),
}
