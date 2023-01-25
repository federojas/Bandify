import React from "react";
import { useTranslation } from "react-i18next";
import "../../styles/welcome.css";
import "../../styles/auditions.css";
import BandAuditionCard from "../../components/BandAuditionCard";
import { Audition } from "../../models";
import PostCard from "../../components/PostCard/PostCard";

const BandAuditionsList = (props: { auditions: Audition[] }) => {
  return (
  <>
  {props.auditions.map((audition, index) => {
    return <PostCard {...audition} />;
    })}
  </>
  );
};

const MyAuditionsList = (props: { auditions: Audition[] }) => {
  return (
    <>
      {props.auditions.map((audition, index) => {
        return <BandAuditionCard {...audition} />;
      })}
    </>
  );
};

const BandAuditions = () => {
  const { t } = useTranslation();

  const isPropietary = false;
  const username = "Dagos";
  let auditionList: Audition[] = [
    {
      band: {
        name: "My Band",
        id: 1,
      },
      id: 1,
      creationDate: new Date(),
      title: "My Band is looking for a drummer",
      roles: ["Drummer"],
      genres: ["Rock"],
      location: "Buenos Aires",
    },
    {
      band: {
        name: "My Band",
        id: 1,
      },
      id: 2,
      creationDate: new Date(),
      title: "My Band is looking for a guitarist",
      roles: ["Guitarist"],
      genres: ["Rock"],
      location: "Buenos Aires",
    },
  ];

  return (
    <div className="auditions-content">
      {isPropietary ? (
        <h2 id="posts">
          {/* replace this message with the appropriate text or variable */}
          {t("bandAuditions.myAuditions")}
        </h2>
      ) : (
        <h2 id="bandAuditions">
          {/* replace this message with the appropriate text or variable */}
          {t("bandAuditions.bandAuditions", { username })}
        </h2>
      )}

      <div className="posts">
        {auditionList.length === 0 && (
          <b>
            <p className="no-auditions">
              {/* replace this message with the appropriate text or variable */}
              {t("bandAuditions.noAuditions")}
            </p>
          </b>
        )}

        {isPropietary ? (
          <MyAuditionsList auditions={auditionList} />
        ) : (
          <BandAuditionsList auditions={auditionList} />
        )}
      </div>
    </div>
  );
};

export default BandAuditions;
