import React, { useState } from 'react';

const urlRegex = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,})(:(\d{1,5}))?(\/.*)?$/i;

export default function ShortButton(props) {
  const [url, setUrl] = useState('');
  const [urlResponse, setUrlResponse] = useState('');
  const [result, setResult] = useState('');
  const [disabled, setDisabled] = useState(true);

  function handleButtonDisabled(e) {
    setResult(<></>);
    setUrlResponse()
    setDisabled(true);
    if (urlRegex.test(e.target.value))
      setDisabled(false);
  }

  const handleCopy = (e) => {
    navigator.clipboard.writeText(urlResponse)
      .then(() => alert('URL copied to clipboard!'))
      .catch(err => console.error('Failed to copy: ', err));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(props.baseUrl, {
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
      const textResponse = await response.text();
      setUrlResponse( textResponse);
      
    
      {console.log(urlResponse);}
      setResult(
        <>
          <h2 className="text-xl font-bold">Generated URL:</h2>
          <a href={textResponse}
            rel="noopener noreferrer"
            target="_blank"
            className="mt-2 text-blue-700 underline">
            {textResponse}
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
    <div className="w-full flex-col">
      <form onSubmit={handleSubmit} className="flex flex-col gap-1 md:gap-2 md:flex-row">
        <input
          className="bg-gray-200 rounded-md px-2 py-1 text-black border-2 border-slate-500 h-6 text-sm w-56 md:h-10 md:text-lg"
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter URL"
          onChangeCapture={handleButtonDisabled}
        />
        <input
          type="submit"
          value="Short ✂️"
          className="rounded-md border-2 border-black text-black px-2 py-1 cursor-pointer hover:text-white hover:bg-black transition h-8 text-sm md:w-24 md:h-10 md:text-lg"
          disabled={disabled}
        />
      </form>
      {result&& (
        <div className="mt-4 text-black text-center">
          {result}
          {urlResponse && <button
              onClick={() => handleCopy(result)}
              className="p-1 ml-3 rounded-md border-2 border-black hover:text-slate-500 hover:border-slate-500"
            >
              Copiar
            </button>}
        </div>
      )}
    </div>
  );
}