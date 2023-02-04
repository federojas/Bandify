import "../../styles/welcome.css";
import "../../styles/auditions.css";
import PostCard from "../../components/PostCard/PostCard";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import {useNavigate, useLocation} from "react-router-dom";
import { Audition } from "../../models";
import {PaginationArrow, PaginationWrapper} from "../../components/Pagination/pagination";
import {usePagination} from "../../hooks/usePagination";
import {useAuditionService} from "../../contexts/AuditionService";
import {getQueryOrDefault, getQueryOrDefaultArray, useQuery} from "../../hooks/useQuery";
import React from "react";
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
  const location = useLocation();
  const auditionService = useAuditionService();
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);
  const query = useQuery();
  const [searchTerms] = React.useState<string>(getQueryOrDefault(query, "query", ""));
  const [roles] = React.useState<string[]>(getQueryOrDefaultArray(query, "role"));
  const [genres] = React.useState<string[]>(getQueryOrDefaultArray(query, "genre"));
  const [locations] = React.useState<string[]>(getQueryOrDefaultArray(query, "location"));
  const [order] = React.useState<string>(getQueryOrDefault(query, "order", ""));

  useEffect(() => {
    serviceCall(
      auditionService.getAuditions(currentPage, searchTerms, roles, genres, locations, order),
      navigate,
      (response) => {
        setAuditions(response ? response.getContent(): []);
        setMaxPage(response ? response.getMaxPage() : 1); //TODO revisar esto
      }
    )
  }, [currentPage, searchTerms, roles, genres, locations, order])

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
        {/*TODO: ver si se puede hacer componente*/}
        <PaginationWrapper>
            {currentPage > 1 && (
                <button
                    onClick={() => {
                        let searchParams = new URLSearchParams(location.search);
                        searchParams.set('page', (currentPage - 1).toString());
                        navigate( {
                            pathname: location.pathname,
                            search: searchParams.toString()
                        });
                    }}
                    style={{ background: "none", border: "none" }}
                >
                    <PaginationArrow
                        xRotated={true}
                        src="../../images/page-next.png"
                        alt={t("Pagination.alt.beforePage")}
                    />
                </button>
            )}
            {t("Pagination.message", {
                currentPage: currentPage,
                maxPage: maxPage,
            })}
            {currentPage < maxPage && (
                <button
                    onClick={() => {
                        let searchParams = new URLSearchParams(location.search);
                        searchParams.set('page', (currentPage + 1).toString());
                        navigate( {
                            pathname: location.pathname,
                            search: searchParams.toString()
                        });
                    }}
                    style={{ background: "none", border: "none" }}
                >
                    <PaginationArrow
                        src="../../images/page-next.png"
                        alt={t("Pagination.alt.nextPage")}
                    />
                </button>
            )}
        </PaginationWrapper>
    </>
  );
};

export default AuditionSearch;
