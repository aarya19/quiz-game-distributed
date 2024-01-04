import React from "react";

const Logo = ({ className }) => {
  return (
    <h1 className={`font-quiz text-5xl transition duration-300 ${className}`}>
      Quiz Master
    </h1>
  );
};

export default Logo;
