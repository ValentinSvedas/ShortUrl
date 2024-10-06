import React, { useState } from 'react';

export default function ShortButton() {
  const [url, setUrl] = useState('');
  const [result, setResult] = useState('');
  const [disabled, setDisabled] = useState(true);

  function handleButtonDisabled(e) {
    setResult(<></>);
    setDisabled(true);
    if (/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/.test(e.target.value))
      setDisabled(false);
  }

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
        <>
        <h2 className="text-xl font-bold">Generated URL:</h2>
        <a href={urlResponse}
          rel="noopener noreferrer"
          target="_blank"
          className="mt-2 text-blue-700 underline">
          {urlResponse}
        </a>
        </>
    );
    
    } catch (error) {
      console.error('Error:', error);
      setResult(
        <p className="mt-2 text-red-400">Error al generar url</p>
      );
    }
    
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
          onChangeCapture={handleButtonDisabled}
        />
        <input
          type="submit"
          value="Shorten URL"
          className="rounded-md bg-black text-white px-2 py-1 cursor-pointer hover:bg-slate-700"
          disabled={disabled}
        />
      </form>
      {result && (
        <div className="mt-4 text-black text-center">
          {result}
        </div>
      )}
    </div>
  );
}