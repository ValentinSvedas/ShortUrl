import React, { useState } from 'react';

export default function ShortButton() {
  const [url, setUrl] = useState('');
  const [result, setResult] = useState('');
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          "url": url
        })
      });
    
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
    
      const urlResponse = await response.text();
    
      setResult(
        <a href={urlResponse}
          rel="noopener noreferrer"
          target="_blank"
          className="mt-2 text-blue-700 underline">
          {urlResponse}
        </a>
    );
    
    } catch (error) {
      console.error('Error:', error);
      setResult(
        <p className="mt-2">Error al generar url</p>
      );
    }
    
  };

  return (
    <div className="w-full flex flex-col gap-3">
      <form onSubmit={handleSubmit} className="flex gap-2">
        <input
          className="bg-gray-200 rounded-md px-2 py-1 text-black"
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter URL"
        />
        <input
          type="submit"
          value="Generate URL"
          className="rounded-md bg-black text-white px-2 py-1 cursor-pointer"
        />
      </form>
      {result && (
        <div className="mt-4 text-black">
          <h2 className="text-xl font-bold">Generated URL:</h2>
          {result}
        </div>
      )}
    </div>
  );
}