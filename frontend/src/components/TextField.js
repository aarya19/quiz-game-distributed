// TextField.js
import React from "react";

const TextField = ({ label, type, placeholder, value, onChange }) => {
  return (
    <div className="mb-2 w-full ">
      <label className="block text-b2 text-lg font-bold mb-2 text-left">
        {label}
      </label>
      <input
        className="appearance-none border rounded-lg w-full py-2 px-3 text-b2 leading-tight focus:outline-none focus:shadow-outline"
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
      />
    </div>
  );
};

export default TextField;
