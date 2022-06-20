import axios from "axios";
import { refresh, refreshError } from "./refresh";
const api = axios.create({
  baseURL: "https://server.promisor.site",
});

api.interceptors.request.use(refresh, refreshError);

export default api;
