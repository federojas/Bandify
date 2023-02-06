import PostCard from "../../components/PostCard/PostCard";
import { useTranslation } from "react-i18next";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import { Audition } from "../../models";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { usePagination } from "../../hooks/usePagination";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../components/Pagination/pagination";
import { useLocation, useNavigate, Link } from "react-router-dom";
import { useAuditionService } from "../../contexts/AuditionService";
import Pagination from "@choc-ui/paginator";

export default function AuditionsPage() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const auditionService = useAuditionService();
  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);
  const [previousPage, setPreviousPage] = useState("");
  const [nextPage, setNextPage] = useState("");


    const location = useLocation();
  useEffect(() => {
    serviceCall(
      auditionService.getAuditions(currentPage),
      navigate,
      (response) => {
        setAuditions(response ? response.getContent() : []);
        setMaxPage(response ? response.getMaxPage() : 1); //TODO revisar esto
        setPreviousPage(response ? response.getPreviousPage() : "");
        setNextPage(response ? response.getPreviousPage() : "");
      },
      location
    )
  }, [currentPage, navigate, auditionService])

  return (
    <>
      <Center marginY={10} flexDirection="column">
        <VStack spacing={5} >
          <Heading fontSize={40}>{t("Auditions.header")}</Heading>
          <AuditionSearchBar />
        </VStack>
      </Center>
      <Divider />
      <VStack marginY={10} alignItems={"center"} spacing={4}>
        <Heading>{t("Auditions.latest")}</Heading>

        <Flex
          direction={"row"}
          wrap={"wrap"}
          margin={2}
          justifyContent={"space-around"}
        >
          {auditions.length > 0 ? auditions.map((audition) => (
            <PostCard {...audition} />
          )) : <Heading>{t("Auditions.noFound")}</Heading>
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
        {/*<Pagination*/}
        {/*  defaultCurrent={currentPage}*/}
        {/*  total={maxPage * 9}*/}
        {/*  pageSize={9}*/}
        {/*  paginationProps={{*/}
        {/*    display: "flex",*/}
        {/*  }}*/}
        {/*  onChange={(page) => {*/}
        {/*    let searchParams = new URLSearchParams(location.search);*/}
        {/*    searchParams.set('page', String(page));*/}
        {/*    navigate({*/}
        {/*      pathname: location.pathname,*/}
        {/*      search: searchParams.toString()*/}
        {/*    });*/}
        {/*  }*/}
        {/*  }*/}
        {/*  colorScheme="blue"*/}
        {/*  rounded="full"*/}
        {/*/>*/}
      <PaginationWrapper>
        {currentPage > 1 && (
          <button
            onClick={() => {
              navigate(previousPage);
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
              navigate(nextPage);
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
}
