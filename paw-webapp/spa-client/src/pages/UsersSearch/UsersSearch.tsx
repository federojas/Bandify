import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useUserService } from "../../contexts/UserService";
import { usePagination } from "../../hooks/usePagination";
import { User } from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import SearchForm from "../../components/SearchBars/DiscoverSearchBar";
import { PaginationArrow, PaginationWrapper } from "../../components/Pagination/pagination";
import ProfileCard from "../../components/ProfileCard/ProfileCard";

const UsersSearch = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [users, setUsers] = useState<User[]>([]);

  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);
  const location = useLocation();
  const userService = useUserService();

  useEffect(() => {
    serviceCall(
      userService.getUsers(currentPage),
      navigate,
      (response) => {
        console.log(response)
        setUsers(response ? response.getContent() : []);
        setMaxPage(1); //TODO revisar esto
      },
      location
    )
  }, [currentPage, navigate, userService])

  return (
    <>
      <Center marginY={10} flexDirection="column">
        <VStack spacing={5} >
          <Heading fontSize={40}>{t("Discover.discover")}</Heading>
          <SearchForm />
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
            <ProfileCard {...user}/>
          )) : <Heading>No auditions found</Heading>
          }
        </Flex>
      </VStack>
      <PaginationWrapper>
        {currentPage > 1 && (
          <Link
            to={`/auditions?page=${currentPage - 1
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
            to={`/auditions?page=${currentPage + 1
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
}

export default UsersSearch