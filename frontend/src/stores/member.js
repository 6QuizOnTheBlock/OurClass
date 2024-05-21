import { create } from "zustand";

const useUserStore = create((set) => ({
  userInfo: null,
  setUserInfo: (userInfo) => set({ userInfo }),
  clearUserInfo: () => set({ userInfo: null }),
  accessToken: null,
  setAccessToken: (accessToken) => set({ accessToken }),
  clearAccessToken: () => set({accessToken: null})
}));

export default useUserStore;