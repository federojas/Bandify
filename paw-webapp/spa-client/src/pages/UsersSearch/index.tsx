import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useUserService } from "../../contexts/UserService";
import { usePagination } from "../../hooks/usePagination";
import { User } from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import { Center, Divider, Flex, Heading, useColorModeValue, VStack } from "@chakra-ui/react";
import { PaginationArrow, PaginationWrapper } from "../../components/Pagination/pagination";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import { getQueryOrDefault, getQueryOrDefaultArray, useQuery } from "../../hooks/useQuery";
import DiscoverSearchBar from "../../components/SearchBars/DiscoverSearchBar";
import Pagination from "@choc-ui/paginator";

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

  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);
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
        setMaxPage(response ? response.getMaxPage() : 1);; //TODO revisar esto
      },
      location
    )
  }, [currentPage, userService, searchTerms, genres, roles, locations, navigate])

  return (
    <>
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
      {/*TODO: ver si se puede hacer componente*/}
      <Flex
        w="full"

        p={50}
        alignItems="center"
        justifyContent="center"
      >
        <Pagination
          defaultCurrent={currentPage}
          total={maxPage * 8}
          pageSize={8}
          paginationProps={{
            display: "flex",
          }}
          onChange={(page) => {
            let searchParams = new URLSearchParams(location.search);
            searchParams.set('page', String(page));
            navigate({
              pathname: location.pathname,
              search: searchParams.toString()
            });
          }
          }
          colorScheme="blue"
          rounded="full"
        />
      </Flex>;

      {/* <PaginationWrapper>
        {currentPage > 1 && (
          <button
            onClick={() => {
              let searchParams = new URLSearchParams(location.search);
              searchParams.set('page', (currentPage - 1).toString());
              navigate({
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
              navigate({
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
      </PaginationWrapper> */}
    </>
  );
}

export default Index