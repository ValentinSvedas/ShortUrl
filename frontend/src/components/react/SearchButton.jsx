import React, { useState } from 'react';

export default function SearchButton() {
  const [url, setUrl] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const parts = url.split('/');
    const result = parts[parts.length - 1];

    window.location.href = `http://localhost:4321/${result}`;
  };

  return (
    <div className="w-full flex gap-3 flex-col">
      <form onSubmit={handleSubmit} className="flex flex-col gap-2 md:flex-row">
        <input
          className="bg-gray-200 rounded-md px-2 py-1 text-black border-2 border-slate-500"
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter URL"
        />
        <input
          type="submit"
          value="Find URL"
          className="rounded-md bg-black text-white px-2 py-1 cursor-pointer hover:bg-slate-700"
        />
      </form>
    </div>
  );
}