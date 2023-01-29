import "../../styles/welcome.css";
import "../../styles/auditions.css";
import NextIcon from "../../assets/icons/page-next.png";
import PostCard from "../../components/PostCard/PostCard";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate } from "react-router-dom";
import { auditionService } from "../../services";
import { Audition } from "../../models";
// type Audition = {
//   band: {
//     name: string;
//     id: number;
//   };
//   id: number;
//   creationDate: Date;
//   title: string;
//   roles: string[];
//   genres: string[];
//   location: string;
// };

// type AuditionListPaginated = {
//   auditionList: Audition[];
//   currentPage: number;
//   totalPages: number;
// };

const AuditionSearch = () => {
  const { t } = useTranslation();
  const getPaginationURL = (page: number) => {
    // TODO: Add code to get pagination URL
  };
  const navigate = useNavigate();
  const [auditions, setAuditions] = useState<Audition[]>([]);
 
  useEffect(() => {
    serviceCall(
      auditionService.getAuditions(),
      navigate,
      (response) => {
        console.log("🚀 ~ file: index.tsx:20 ~ useEffect ~ response", response)
        setAuditions(response)
      }
    )
  }, [])

  return (
    <>
      <Center marginY={10} flexDirection="column">
        <VStack spacing={5}>
          <Heading>{t("AuditionsSearch.results")}</Heading>

          <AuditionSearchBar />
        </VStack>
      </Center>
      <Flex
        direction={"row"}
        wrap={"wrap"}
        margin={2}
        justifyContent={"space-around"}
      >
        {auditions.length === 0 && (
          <b>
            <p className="no-results">{t("AuditionsSearch.noResults")}</p>
          </b>
        )}
        {auditions.map((audition, index) => (
          <PostCard {...audition} />
        ))}
      </Flex>
      {/* <div className="pagination">
        {auditionList.currentPage > 1 && (
          <a onClick={() => getPaginationURL(auditionList.currentPage - 1)}>
            <img
              src={NextIcon}
              alt="previous"
              className="pagination-next rotate"
            />
          </a>
        )}
        <b>
          {t("Pagination.page")} {auditionList.currentPage} {t("Pagination.of")} {auditionList.totalPages}
        </b>
        {auditionList.currentPage < auditionList.totalPages && (
          <a onClick={() => getPaginationURL(auditionList.currentPage + 1)}>
            <img src={NextIcon} alt="next" className="pagination-next" />
          </a>
        )}
      </div> */}
    </>
  );
};

export default AuditionSearch;
