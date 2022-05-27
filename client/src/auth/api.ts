import axios from "axios";
import { refresh, refreshErrorHandler } from "./refresh";
const api = axios.create({
  baseURL: "https://promisor.site",
});

api.interceptors.request.use(refresh, refreshErrorHandler);

export default api;
