import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useLocation, useNavigate } from "react-router-dom";
import { useUserService } from "../../contexts/UserService";
import { User } from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { PaginationWrapper } from "../../components/Pagination/pagination";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import { getQueryOrDefault, getQueryOrDefaultArray, useQuery } from "../../hooks/useQuery";
import DiscoverSearchBar from "../../components/SearchBars/DiscoverSearchBar";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import { Helmet } from "react-helmet";

interface SearchParams {
  searchTerms?: string;
  roles?: string[];
  genres?: string[];
  locations?: string[];
}

const Index = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [users, setUsers] = useState<User[]>([]);
  const query = useQuery();
  const [currentPage, setCurrentPage] = useState(parseInt(getQueryOrDefault(query, "page", "1")));
  const [maxPage, setMaxPage] = useState(1);
  const [previousPage, setPreviousPage] = useState("");
  const [nextPage, setNextPage] = useState("");
  const location = useLocation();
  const userService = useUserService();
  const [searchTerms, setSearchTerms] = React.useState<string>(getQueryOrDefault(query, "query", ""));
  const [roles, setRoles] = React.useState<string[]>(getQueryOrDefaultArray(query, "role"));
  const [genres, setGenres] = React.useState<string[]>(getQueryOrDefaultArray(query, "genre"));
  const [locations, setLocations] = React.useState<string[]>(getQueryOrDefaultArray(query, "location"));

  const handleSubmit = (searchParams: SearchParams) => {
    // update state with new search parameters
    setSearchTerms(searchParams.searchTerms || searchTerms);
    setRoles(searchParams.roles || roles);
    setGenres(searchParams.genres || genres);
    setLocations(searchParams.locations || locations);
  };

  useEffect(() => {
    serviceCall(
      userService.getUsers(currentPage, searchTerms, genres, roles, locations),
      navigate,
      (response) => {
        setUsers(response ? response.getContent() : []);
        setMaxPage(response ? response.getMaxPage() : 1);
        setNextPage(response ? response.getNextPage() : "");
        setPreviousPage( response ? response.getPreviousPage() : "");
      },
      location
    )
  }, [userService, searchTerms, genres, roles, locations, navigate, location])

  return (
    <>
    <Helmet>
          <title>{t("Discover.discover")}</title>
    </Helmet>
      <Center marginY={10} flexDirection="column">
        <VStack spacing={5} >
          <Heading fontSize={40}>{t("Discover.discover")}</Heading>
          <DiscoverSearchBar onSubmit={handleSubmit} />
        </VStack>
      </Center>
      <Divider />
      <VStack marginY={10} alignItems={"center"} spacing={4}>
        <Heading>{t("Discover.results")}</Heading>

        <Flex
          direction={"row"}
          wrap={"wrap"}
          margin={2}
          gap={4}
          justifyContent={"space-around"}
        >
          {users.length > 0 ? users.map((user) => (
            <ProfileCard {...user} />
          )) : <b>
            <p className="no-results">{t("Discover.noResults")}</p>
          </b>
          }
        </Flex>
      </VStack>
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
                        userService.getUsersByUrl(previousPage),
                        navigate,
                        (response) => {
                          setUsers(response ? response.getContent() : []);
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
            <ChevronLeftIcon mr={4}/>

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
                        userService.getUsersByUrl(nextPage),
                        navigate,
                        (response) => {
                          setUsers(response ? response.getContent() : []);
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
            <ChevronRightIcon ml={4}/>

              </button>
          )}
        </PaginationWrapper>
      </Flex>
    </>
  );
}

export default Index