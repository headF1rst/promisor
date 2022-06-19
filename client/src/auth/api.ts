import axios from "axios";
import { refresh } from "./refresh";
const api = axios.create({
  baseURL: "https://server.promisor.site",
});

api.interceptors.request.use(refresh);

export default api;
