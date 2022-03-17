import React from "react";
import * as S from "../styles/_index";
import ArrowBack from "../atoms/ArrowBack";
import { AnimatePresence } from "framer-motion";
interface IBasedTemplate {
  header: object;
  container: object;
}
function BasedTemplate({ header, container }: IBasedTemplate) {
  return (
    <AnimatePresence>
      <S.Template
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
      >
        <S.Header>{header}</S.Header>
        <S.Container>{container}</S.Container>
      </S.Template>
    </AnimatePresence>
  );
}

export default BasedTemplate;
