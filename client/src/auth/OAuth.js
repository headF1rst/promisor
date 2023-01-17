const REST_API_KEY = "b7a18cd68f31fdba3ce47cf47322c9a1";
const REDIRECT_URI = "http://localhost:8000/oauth/kakao";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
