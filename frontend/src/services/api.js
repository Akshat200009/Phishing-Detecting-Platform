const API_BASE_URL = import.meta.env.VITE_API_URL ?? ''

async function request(path, options = {}) {
  const token = localStorage.getItem('phishguard_token')
  const response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers: { 'Content-Type': 'application/json', ...(token ? { Authorization: `Bearer ${token}` } : {}), ...options.headers } })
  if (!response.ok) {
    const body = await response.text()
    let message = body
    try { message = JSON.parse(body).message || body } catch { }
    throw new Error(message || 'Something went wrong. Please try again.')
  }
  return response.status === 204 ? null : response.json()
}

export const api = {
  login: (email, password) => request('/api/auth/login', { method: 'POST', body: JSON.stringify({ email, password }) }),
  register: (name, email, password) => request('/api/auth/register', { method: 'POST', body: JSON.stringify({ name, email, password }) }),
  logout: () => request('/api/auth/logout', { method: 'POST' }),
  scanUrl: (url) => request('/api/url/scan', { method: 'POST', body: JSON.stringify({ url }) }),
  scanEmail: (email) => request('/api/email/scan', { method: 'POST', body: JSON.stringify(email) }),
  getScanReport: () => request('/api/history/reports'),
  getScanHistory: (filters = {}) => {
    const query = new URLSearchParams(Object.entries(filters).filter(([, value]) => value))
    return request(`/api/history${query.size ? `?${query}` : ''}`)
  },
  getAdminStatistics: () => request('/api/admin/dashboard/statistics'),
  getAdminThreatAnalytics: () => request('/api/admin/dashboard/threat-analytics'),
  getAdminScans: () => request('/api/admin/dashboard/scans'),
  getAdminUsers: () => request('/api/users/admin/users'),
  updateUserRole: (id, role) => request(`/api/users/admin/users/${id}/role`, { method: 'PUT', body: JSON.stringify({ role }) }),
  updateUserStatus: (id, active) => request(`/api/users/admin/users/${id}/status`, { method: 'PUT', body: JSON.stringify({ active }) }),
}
