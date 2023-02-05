import React, { useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import "../../styles/welcome.css";
import "../../styles/auditions.css";
import { Audition } from "../../models";
import { Flex, Heading, VStack } from "@chakra-ui/react";
import AuthContext from "../../contexts/AuthContext";
import { serviceCall } from "../../services/ServiceManager";
import { useAuditionService } from "../../contexts/AuditionService";
import {useLocation, useNavigate} from "react-router-dom";
import { usePagination} from "../../hooks/usePagination";
import {
    PaginationArrow,
    PaginationWrapper,
} from "../../components/Pagination/pagination";
import PostCard from "../../components/PostCard/PostCard";

const BandAuditions = () => {
  const { t } = useTranslation();
  const { userId } = useContext(AuthContext);
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const auditionService = useAuditionService();
  const navigate = useNavigate();
  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);
  const location = useLocation();

  useEffect(() => {
    serviceCall(
      auditionService.getAuditionsByBandId(currentPage, userId),
      navigate,
      (auditions) => {
        setAuditions(auditions ? auditions.getContent() : []);
        setMaxPage(auditions ? auditions.getMaxPage() : 1);
      }
    )
  }, [currentPage, navigate, auditionService])

  return (
      <>
      <VStack marginY={10} alignItems={"center"} spacing={4}>
        <Heading>{t("MyAuditions.title")}</Heading>

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
  )
}

export default BandAuditions;
