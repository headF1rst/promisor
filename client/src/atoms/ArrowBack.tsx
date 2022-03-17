import React from "react";
import { IoIosArrowBack } from "react-icons/io";
import { useNavigate } from "react-router-dom";

function ArrowBack() {
  const navigate = useNavigate();

  const onArrowBackClick = () => {
    navigate(-1);
  };
  return (
    <IoIosArrowBack style={{ cursor: "pointer" }} onClick={onArrowBackClick} />
  );
}

export default ArrowBack;
