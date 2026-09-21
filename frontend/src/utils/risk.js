export function getRiskLevel(score) {
  if (score >= 70) return { label: 'High risk', style: 'border-rose-400/20 bg-rose-400/10 text-rose-300' }
  if (score >= 35) return { label: 'Needs caution', style: 'border-amber-400/20 bg-amber-400/10 text-amber-300' }
  return { label: 'Low risk', style: 'border-emerald-400/20 bg-emerald-400/10 text-emerald-300' }
}

export function getUrlSignals(result) {
  return [['HTTPS connection', result.https], ['Suspicious keywords', result.suspiciousKeyword], ['Suspicious domain', result.suspiciousDomain], ['IP address URL', result.ipAddress]]
}
