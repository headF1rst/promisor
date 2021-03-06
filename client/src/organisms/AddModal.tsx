import { AnimatePresence, motion } from "framer-motion";
import React from "react";
import styled from "styled-components";
import * as A from "../atoms/_index";
import * as S from "../styles/_index";

interface IAddModal {
  props: {
    titleText: string;
    placeholderText?: string;
    btnText: string;
    onBtnClick: React.MouseEventHandler;
    showModal: boolean;
    setShowModal: Function;
    inputValue?: string;
    setInputValue?: Function;
  };
}

function AddModal({ props }: IAddModal) {
  const onModalToggle = () => {
    props.setShowModal((p: boolean) => !p);
    props.setInputValue("");
  };
  const opacityVariants = {
    initial: {
      opacity: 0,
    },
    animate: {
      opacity: 1,
    },
    exit: {
      opacity: 0,
    },
  };
  const onValueChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value },
    } = e;
    props.setInputValue && props.setInputValue(value);
  };
  return (
    <AnimatePresence>
      {props.showModal && (
        <>
          <Modal
            key="addModal"
            variants={opacityVariants}
            initial="initial"
            animate="animate"
            exit="exit"
          >
            <span>{props.titleText}</span>
            {props.placeholderText && (
              <S.LabelInput>
                <S.Input
                  placeholder={props.placeholderText}
                  value={props.inputValue}
                  onChange={onValueChange}
                />
              </S.LabelInput>
            )}
            <A.RoundBtn
              onClick={props.onBtnClick}
              center={true}
              value={props.btnText}
            />
          </Modal>
          <S.Overlay
            key={"addOverlay"}
            variants={opacityVariants}
            initial="initial"
            animate="animate"
            exit="exit"
            onClick={onModalToggle}
          />
        </>
      )}
    </AnimatePresence>
  );
}

export default AddModal;

const Modal = styled(motion.div)`
  display: flex;
  flex-direction: column;
  position: fixed;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  color: ${(p) => p.theme.textColor};
  width: 80%;
  @media screen and (min-width: 900px) {
    width: 40%;
  }
  height: 14em;
  padding: 2em;
  justify-content: space-between;
  z-index: 3;
  span {
    background-color: transparent;
  }
`;
