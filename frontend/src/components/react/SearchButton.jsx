import React, { useState } from 'react';


export default function SearchButton(props) {
  const baseFEUrl = import.meta.env.PUBLIC_FE_BASE_URL;
  const [url, setUrl] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const parts = url.split('/');
    const result = parts[parts.length - 1];

    window.location.href = `${baseFEUrl}${result}`;
  };

  return (
    <div className="w-full flex gap-3 md:gap-3 flex-col">
      <form onSubmit={handleSubmit} className="flex flex-col gap-2 md:flex-row">
        <input
          className="bg-gray-200 rounded-md px-2 py-1 text-black border-2 border-slate-500 h-6 text-sm w-56 md:h-10 md:text-lg"
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter URL"
        />
        <input
          type="submit"
          value="Find 🔎"
          className="rounded-md border-2 border-black text-black px-2 py-1 cursor-pointer hover:text-white hover:bg-black transition h-8 text-sm md:w-24 md:h-10 md:text-lg"
        />
      </form>
    </div>
  );
}