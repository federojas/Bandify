import "../../styles/welcome.css";
import "../../styles/auditions.css";
import PostCard from "../../components/PostCard/PostCard";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import {
  Box, Button,
  Center,
  Container,
  Divider,
  Flex,
  FormLabel,
  Heading, HStack,
  Input,
  Text,
  useColorModeValue,
  VStack
} from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate, useLocation } from "react-router-dom";
import { Audition } from "../../models";
import { PaginationArrow, PaginationWrapper } from "../../components/Pagination/pagination";
import { useAuditionService } from "../../contexts/AuditionService";
import { getQueryOrDefault, getQueryOrDefaultArray, useQuery } from "../../hooks/useQuery";
import React from "react";

interface SearchParams {
  searchTerms?: string;
  roles?: string[];
  genres?: string[];
  locations?: string[];
  order?: string;
}

const AuditionSearch = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const location = useLocation();
  const query = useQuery();
  const auditionService = useAuditionService();
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const [currentPage, setCurrentPage] = useState(parseInt(getQueryOrDefault(query, "page", "1")));
  const [maxPage, setMaxPage] = useState(1);
  const [previousPage, setPreviousPage] = useState("");
  const [nextPage, setNextPage] = useState("");
  const [searchTerms, setSearchTerms] = React.useState<string>(getQueryOrDefault(query, "query", ""));
  const [roles, setRoles] = React.useState<string[]>(getQueryOrDefaultArray(query, "role"));
  const [genres, setGenres] = React.useState<string[]>(getQueryOrDefaultArray(query, "genre"));
  const [locations, setLocations] = React.useState<string[]>(getQueryOrDefaultArray(query, "location"));
  const [order, setOrder] = React.useState<string>(getQueryOrDefault(query, "order", ""));

  const handleSubmit = (searchParams: SearchParams) => {
    // update state with new search parameters
    setSearchTerms(searchParams.searchTerms || searchTerms);
    setRoles(searchParams.roles || roles);
    setGenres(searchParams.genres || genres);
    setLocations(searchParams.locations || locations);
    setOrder(searchParams.order || order);
  };

  useEffect(() => {
    serviceCall(
      auditionService.getAuditions(currentPage, searchTerms, roles, genres, locations, order),
      navigate,
      (response) => {
        setAuditions(response ? response.getContent() : []);
        setMaxPage(response ? response.getMaxPage() : 1); //TODO revisar esto
        setPreviousPage(response ? response.getPreviousPage() : "");
        setNextPage(response ? response.getNextPage() : "");
      }
    )
  }, [currentPage, searchTerms, roles, genres, locations, order])

  return (
    <>
      <Center marginY={10} flexDirection="column">
        <VStack spacing={5}>
          <Heading>{t("AuditionsSearch.results")}</Heading>
          <AuditionSearchBar onSubmit={handleSubmit} />
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
      <Flex
        w="full"
        p={50}
        alignItems="center"
        justifyContent="center"
      >
        <PaginationWrapper>
          {currentPage > 1 && (
              <button
                  onClick={() => {
                    serviceCall(
                        auditionService.getAuditionsByUrl(previousPage),
                        navigate,
                        (response) => {
                          setAuditions(response ? response.getContent() : []);
                          setPreviousPage(response ? response.getPreviousPage() : "");
                          setNextPage(response ? response.getNextPage() : "");
                        },
                        location
                    )
                    setCurrentPage(currentPage - 1)
                    const url = new URL(window.location.href);
                    url.searchParams.set('page', String(currentPage - 1));
                    window.history.pushState(null, '', url.toString());
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
                    serviceCall(
                        auditionService.getAuditionsByUrl(nextPage),
                        navigate,
                        (response) => {
                          setAuditions(response ? response.getContent() : []);
                          setPreviousPage(response ? response.getPreviousPage() : "");
                          setNextPage(response ? response.getNextPage() : "");
                        },
                        location
                    )
                    setCurrentPage(currentPage + 1)
                    const url = new URL(window.location.href);
                    url.searchParams.set('page', String(currentPage + 1));
                    window.history.pushState(null, '', url.toString());
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
      </Flex>
    </>
  );
};

export default AuditionSearch;
