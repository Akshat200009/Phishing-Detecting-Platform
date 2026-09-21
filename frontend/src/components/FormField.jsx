export default function FormField({ label, ...props }) {
  return <label className="block text-sm font-medium text-slate-300">{label}<input required className="mt-2 block w-full rounded-xl border border-slate-700 bg-slate-900 px-4 py-3 text-slate-100 outline-none transition placeholder:text-slate-600 focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/20" {...props} /></label>
}
