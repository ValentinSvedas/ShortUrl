const pad = (num) => num.toString().padStart(2, '0');

const formatDate = (dateString) => {
  const date = new Date(dateString);

  return `${pad(date.getMonth() + 1)}/${pad(date.getDate())}/${date.getFullYear()} ` +
         `${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
};

  export default formatDate;  