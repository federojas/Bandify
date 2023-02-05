import React, { useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import "../../styles/welcome.css";
import "../../styles/auditions.css";
import { Audition } from "../../models";
import {
    Button, ButtonGroup, Card,
    CardBody, CardFooter, Divider,
    Flex,
    Heading,
    HStack,
    Link, Stack,
    Text,
    useToast,
    VStack
} from "@chakra-ui/react";
import {FiCalendar, FiMusic, FiShare2} from "react-icons/fi";
import { AiOutlineInfoCircle } from "react-icons/ai";
import dayjs from "dayjs";
import AuthContext from "../../contexts/AuthContext";
import { serviceCall } from "../../services/ServiceManager";
import { useAuditionService } from "../../contexts/AuditionService";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import { usePagination} from "../../hooks/usePagination";
import Pagination from "@choc-ui/paginator";
import {BiBullseye} from "react-icons/bi";
import GenreTag from "../../components/Tags/GenreTag";
import RoleTag from "../../components/Tags/RoleTag";

const PublicBandAudition = (
    {
        audition,
    }: {
        audition: Audition
    }
) => {
        const { t } = useTranslation();
        const date = dayjs(audition.creationDate).format('DD/MM/YYYY')
        const toast = useToast();
        const { userId } = useContext(AuthContext);

        return (
            <Card maxW="md" margin={5} boxShadow={"2xl"} w={"2xl"}>
                <CardBody>
                    <Stack spacing="3">
                        <Heading size="md" noOfLines={1}>{audition.title}</Heading>
                        <HStack spacing={4}>
                            <FiCalendar />
                            <HStack wrap={'wrap'}>
                                <Text>{date}</Text>
                            </HStack>
                        </HStack>
                        <HStack spacing={4}>
                            <BiBullseye />
                            <HStack spacing="2" wrap={"wrap"}>
                                {audition.lookingFor.map((role) => (
                                    <RoleTag role={role} />
                                ))}
                            </HStack>
                        </HStack>
                        <HStack spacing={4}>
                            <FiMusic />
                            <HStack spacing="2" wrap={"wrap"}>
                                {audition.musicGenres.map((genre) => (
                                    <GenreTag genre={genre} />
                                ))}
                            </HStack>
                        </HStack>
                    </Stack>
                </CardBody>
                <Divider />
                <CardFooter>
                    <ButtonGroup>
                        <Link to={"/auditions/" + audition.id.toString()}>
                            <Button variant="solid" colorScheme="blue" leftIcon={<AiOutlineInfoCircle />}>
                                {t("PostCard.more")}
                            </Button>
                        </Link>
                        <Button variant="ghost" colorScheme="blue" leftIcon={<FiShare2 />}
                                onClick={() => {
                                    navigator.clipboard.writeText(window.location.href + "/" + audition.id.toString())
                                    toast({
                                        title: t("Register.success"),
                                        status: "success",
                                        description: t("Clipboard.message"),
                                        isClosable: true,
                                    });
                                }}

                        >                {t("PostCard.share")}
                        </Button>
                    </ButtonGroup>
                </CardFooter>
            </Card>
        )
}


const PublicBandAuditions = () => {
    const { t } = useTranslation();
    const { userId } = useParams();
    const [auditions, setAuditions] = useState<Audition[]>([]);
    const auditionService = useAuditionService();
    const navigate = useNavigate();
    const [currentPage] = usePagination();
    const [maxPage, setMaxPage] = useState(1);
    const location = useLocation();

    useEffect(() => {
        serviceCall(
            auditionService.getAuditionsByBandId(currentPage, Number(userId)),
            navigate,
            (auditions) => {
                setAuditions(auditions ? auditions.getContent() : []);
                setMaxPage(auditions ? auditions.getMaxPage() : 1);
            }
        )
    }, [currentPage, navigate, auditionService])

    return (
        <VStack pt={'10'}>
            <Heading as='h1'
                     size='2xl'
                     fontWeight="bold">{t("MyAuditions.title")}</Heading>
            <Flex
                p={50}
                w="full"
                alignItems="center"
                direction={"row"}
                wrap={"wrap"}
                margin={2}
                justifyContent={"space-around"}
            >
                {auditions.length > 0 ?
                    auditions.map((audition, index) => {
                        return <PublicBandAudition audition={audition} key={index} />
                    }) : <></>
                }
            </Flex>
            <Flex
                w="full"
                p={50}
                alignItems="center"
                justifyContent="center"
            >
                <Pagination
                    defaultCurrent={currentPage}
                    total={maxPage * 9}
                    pageSize={9}
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
            </Flex>
        </VStack>
    )
}

export default PublicBandAuditions;
