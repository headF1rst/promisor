import React from "react";
interface IIconText {
  icon: Object;
  text: string;
}
export function IconText({ icon, text }: IIconText) {
  return (
    <span>
      <span
        style={{
          position: "relative",
          marginRight: "0.2em",
          top: "0.1em",
        }}
      >
        {icon}
      </span>
      {text}
    </span>
  );
}
