import React, { useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import "../../styles/welcome.css";
import "../../styles/auditions.css";
import BandAuditionCard from "../../components/BandAuditionCard";
import { Audition } from "../../models";
import PostCard from "../../components/PostCard/PostCard";
import { Box, Button, Flex, Heading, HStack, Link, Text, useColorModeValue, VStack } from "@chakra-ui/react";
import { FiCalendar, FiUsers } from "react-icons/fi";
import { AiOutlineInfoCircle } from "react-icons/ai";
import dayjs from "dayjs";
import { ImLocation } from "react-icons/im";
import AuthContext from "../../contexts/AuthContext";
import { serviceCall } from "../../services/ServiceManager";
import { useAuditionService } from "../../contexts/AuditionService";
import { useNavigate } from "react-router-dom";
import { usePagination} from "../../hooks/usePagination";


// const BandAuditionsList = (props: { auditions: Audition[] }) => {
//   return (
//     <>
//       {props.auditions.map((audition, index) => {
//         return <PostCard {...audition} />;
//       })}
//     </>
//   );
// };

// const MyAuditionsList = (props: { auditions: Audition[] }) => {
//   return (
//     <>
//       {props.auditions.map((audition, index) => {
//         return <BandAuditionCard {...audition} />;
//       })}
//     </>
//   );
// };

// const BandAuditions = () => {
//   const { t } = useTranslation();

//   const isPropietary = false;
//   const username = "Dagos";
//   let auditionList: Audition[] = [
//     {
//       band: {
//         name: "My Band",
//         id: 1,
//       },
//       id: 1,
//       creationDate: new Date(),
//       title: "My Band is looking for a drummer",
//       roles: ["Drummer"],
//       genres: ["Rock"],
//       location: "Buenos Aires",
//     },
//     {
//       band: {
//         name: "My Band",
//         id: 1,
//       },
//       id: 2,
//       creationDate: new Date(),
//       title: "My Band is looking for a guitarist",
//       roles: ["Guitarist"],
//       genres: ["Rock"],
//       location: "Buenos Aires",
//     },
//   ];

//   return (
//     <div className="auditions-content">
//       {isPropietary ? (
//         <h2 id="posts">
//           {/* replace this message with the appropriate text or variable */}
//           {t("bandAuditions.myAuditions")}
//         </h2>
//       ) : (
//         <h2 id="bandAuditions">
//           {/* replace this message with the appropriate text or variable */}
//           {t("bandAuditions.bandAuditions", { username })}
//         </h2>
//       )}

//       <div className="posts">
//         {auditionList.length === 0 && (
//           <b>
//             <p className="no-auditions">
//               {/* replace this message with the appropriate text or variable */}
//               {t("bandAuditions.noAuditions")}
//             </p>
//           </b>
//         )}

//         {isPropietary ? (
//           <MyAuditionsList auditions={auditionList} />
//         ) : (
//           <BandAuditionsList auditions={auditionList} />
//         )}
//       </div>
//     </div>
//   );
// };

const BandAudition = (
  {
    audition,
  }: {
    audition: Audition
  }
) => {
  const bg = useColorModeValue("gray.100", "gray.900")
  const { t } = useTranslation();
  const date = dayjs(audition.creationDate).format('DD/MM/YYYY')
  const navigate = useNavigate()
  return (

    <Box
      w="full"
      maxW="sm"
      mx="auto"
      my="6"
      px={4}
      py={3}
      bg={bg}
      shadow="md"
      rounded="md"
    >

      <Heading
        as='h1'
        fontSize="lg"
        fontWeight="bold"
        m={2}
        noOfLines={1}
        color="gray.800"
        _dark={{
          color: "white",
        }}
      >
        {audition.title}
      </Heading>

      <Flex justify="space-around">
        <HStack spacing={4}>
          <ImLocation />
          <Text fontSize={"lg"}>{audition.location}</Text>
        </HStack>
        <HStack spacing={4}>
          <FiCalendar />
          <HStack wrap={'wrap'}>
            <Text>{date}</Text>
          </HStack>
        </HStack>
      </Flex>


      <Flex alignItems="center" justifyContent="center" mt={4}>
        <Button colorScheme="green" mr={3} leftIcon={<FiUsers />}>
          {t("MyAuditions.applicants")}
        </Button>
        <Button colorScheme="blue" leftIcon={<AiOutlineInfoCircle />}
          onClick={() => navigate('/auditions/' + audition.id)}
        >{t("MyAuditions.moreInfo")}</Button>

      </Flex>
    </Box>

  )
}

const BandAuditions = () => {
  const { t } = useTranslation();
  const { userId } = useContext(AuthContext);
  const [auditions, setAuditions] = useState<Audition[]>([]);
  const auditionService = useAuditionService();
  const navigate = useNavigate();
  const [currentPage] = usePagination();
  const [maxPage, setMaxPage] = useState(1);

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
          return <BandAudition audition={audition} key={index} />
        }) : <Text></Text>
        }
        {/* <BandAudition />
        <BandAudition />
        <BandAudition />
        <BandAudition />
        <BandAudition />
        <BandAudition />
        <BandAudition /> */}

      </Flex>
    </VStack>
  )
}

export default BandAuditions;
