import { getRiskLevel } from '../utils/risk'

const signals = [
  ['Suspicious keywords', 'suspiciousKeyword'],
  ['Urgency language', 'urgencyDetechted'],
  ['Credential request', 'credentialDetected'],
]

export default function EmailScanResult({ result }) {
  if (!result) return <aside className="flex min-h-96 items-center rounded-3xl border border-dashed border-slate-700 bg-slate-900/40 p-8 text-center"><div className="w-full"><div className="mx-auto grid size-14 place-items-center rounded-2xl bg-slate-800 text-2xl text-cyan-300">@</div><h2 className="mt-5 text-lg font-semibold">Your assessment will appear here</h2><p className="mt-2 text-sm leading-6 text-slate-400">We'll identify risky language, credential requests, and links in the message.</p></div></aside>

  const risk = getRiskLevel(result.riskScore)
  return <aside className="rounded-3xl border border-slate-800 bg-slate-900 p-7 shadow-2xl shadow-black/20"><div className="flex items-start justify-between gap-5"><div><p className="text-sm font-medium text-slate-400">Email assessment</p><h2 className="mt-1 text-2xl font-semibold text-white">{risk.label}</h2></div><div className={`grid size-20 place-items-center rounded-full border-8 text-xl font-bold ${risk.style}`}>{result.riskScore}<span className="-ml-4 mt-7 text-xs font-medium">/100</span></div></div><div className="mt-6 space-y-3"><p className="break-all rounded-xl bg-slate-950 px-4 py-3 text-sm text-slate-300">From: {result.sender}</p><p className="rounded-xl bg-slate-950 px-4 py-3 text-sm text-slate-300">Subject: {result.subject}</p></div><p className="mt-3 text-sm text-slate-400">Status: <span className="font-medium text-slate-200">{result.status}</span></p><div className="mt-7 space-y-3 border-t border-slate-800 pt-6">{signals.map(([label, key]) => <div key={key} className="flex items-center justify-between gap-4 text-sm"><span className="text-slate-400">{label}</span><span className={result[key] ? 'text-rose-300' : 'text-emerald-300'}>{result[key] ? 'Flagged' : 'Clear'}</span></div>)}</div><div className="mt-7 border-t border-slate-800 pt-6"><h3 className="text-sm font-medium text-slate-300">Extracted URLs</h3>{result.extractUrls?.length ? <ul className="mt-3 space-y-2">{result.extractUrls.map((url) => <li key={url} className="break-all rounded-lg bg-slate-950 px-3 py-2 text-sm text-cyan-300">{url}</li>)}</ul> : <p className="mt-2 text-sm text-slate-500">No URLs found in this email.</p>}</div></aside>
}
