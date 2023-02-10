import PostCard from "../../components/PostCard/PostCard";
import { useTranslation } from "react-i18next";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import { Audition } from "../../models";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../components/Pagination/pagination";
import { useLocation, useNavigate } from "react-router-dom";
import { useAuditionService } from "../../contexts/AuditionService";
import {getQueryOrDefault, useQuery} from "../../hooks/useQuery";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import { Helmet } from "react-helmet";

export default function AuditionsPage() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const query = useQuery();
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const auditionService = useAuditionService();
  const [currentPage, setCurrentPage] = useState(parseInt(getQueryOrDefault(query, "page", "1")));
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
        setMaxPage(response ? response.getMaxPage() : 1);
        setPreviousPage(response ? response.getPreviousPage() : "");
        setNextPage(response ? response.getNextPage() : "");
      },
      location
    )
  }, [currentPage, navigate, auditionService])

  return (
    <>
      <Helmet>
        <title>{t("Auditions.title")}</title>
      </Helmet>
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
            <ChevronRightIcon ml={4}/>
          </button>
        )}
      </PaginationWrapper>
      </Flex>
    </>
  );
}
