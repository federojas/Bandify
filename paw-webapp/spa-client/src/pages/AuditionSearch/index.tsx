import "../../styles/welcome.css";
import "../../styles/auditions.css";
import PostCard from "../../components/PostCard/PostCard";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import {Link, useNavigate} from "react-router-dom";
import { Audition } from "../../models";
import {PaginationArrow, PaginationWrapper} from "../../components/Pagination/pagination";
import {usePagination} from "../../hooks/usePagination";
import {useAuditionService} from "../../contexts/AuditionService";
import {getQueryOrDefault, getQueryOrDefaultArray, useQuery} from "../../hooks/useQuery";
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
  const navigate = useNavigate();
  const auditionService = useAuditionService();
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);
  const query = useQuery();
  const searchTerms = getQueryOrDefault(query, "query", "");
  const roles = getQueryOrDefaultArray(query, "role");
  const genres = getQueryOrDefaultArray(query, "genre");
  const locations = getQueryOrDefaultArray(query, "location");
  const order = getQueryOrDefault(query, "order", "");
 
  useEffect(() => {
    serviceCall(
      auditionService.getAuditions(currentPage, searchTerms, roles, genres, locations, order),
      navigate,
      (response) => {
        setAuditions(response ? response.getContent(): []);
        setMaxPage(response ? response.getMaxPage() : 1); //TODO revisar esto
      }
    )
  }, [currentPage])

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
        <PaginationWrapper>
            {currentPage > 1 && (
                <Link
                    to={`/auditions?page=${
                        currentPage - 1
                    }`}
                >
                    <PaginationArrow
                        xRotated={true}
                        src="../../images/page-next.png"
                        alt={t("Pagination.alt.beforePage")}
                    />
                </Link>
            )}
            {t("Pagination.message", {
                currentPage: currentPage,
                maxPage: maxPage,
            })}
            {currentPage < maxPage && (
                <Link
                    to={`/auditions?page=${
                        currentPage + 1
                    }`}
                >
                    <PaginationArrow
                        src="../../images/page-next.png"
                        alt={t("Pagination.alt.nextPage")}
                    />
                </Link>
            )}
        </PaginationWrapper>
    </>
  );
};

export default AuditionSearch;
