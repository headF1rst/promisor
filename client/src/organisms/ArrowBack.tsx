import React from "react";
import { IoIosArrowBack } from "react-icons/io";
import { Link } from "react-router-dom";
interface IArrowBack {
  route: string;
}
function ArrowBack({ route }: IArrowBack) {
  return (
    <Link to={`/${route}`}>
      <IoIosArrowBack />
    </Link>
  );
}

export default ArrowBack;
