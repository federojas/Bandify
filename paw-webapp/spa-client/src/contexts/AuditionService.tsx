import { createContext, useContext } from "react";
import AuditionApi from "../api/AuditionApi";
import useAxiosPrivate from "../hooks/useAxiosPrivate"
import AuditionServiceTest from "../services/AuditionServiceTest";
import AuditionApiTest from "../api/AuditionApiTest";

const AuditionServiceContext = createContext<AuditionServiceTest>(null!);

export const useAuditionService = () => useContext(AuditionServiceContext);

export const AuditionServiceProvider = ({ children }: { children: React.ReactNode }) => {
  const axiosPrivate = useAxiosPrivate();

  const auditionApi = new AuditionApiTest(axiosPrivate);
  const auditionService = new AuditionServiceTest(auditionApi);

  return (
    <AuditionServiceContext.Provider value={auditionService} >
      {children}
    </AuditionServiceContext.Provider>
  )
}