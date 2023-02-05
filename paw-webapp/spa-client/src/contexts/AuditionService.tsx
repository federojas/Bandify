import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate"
import AuditionService from "../services/AuditionService";
import AuditionApiTest from "../api/AuditionApi";

const AuditionServiceContext = createContext<AuditionService>(null!);

export const useAuditionService = () => useContext(AuditionServiceContext);

export const AuditionServiceProvider = ({ children }: { children: React.ReactNode }) => {
  const axiosPrivate = useAxiosPrivate();

  const auditionApi = new AuditionApiTest(axiosPrivate);
  const auditionService = new AuditionService(auditionApi);

  return (
    <AuditionServiceContext.Provider value={auditionService} >
      {children}
    </AuditionServiceContext.Provider>
  )
}