import React, { memo } from "react";
import { IonItem, IonLabel } from "@ionic/react";
import { getLogger } from "../core";
import { Movie } from "./Movie";
import styles from "./styles.module.css";

interface MoviePropsExtended extends Movie {
    onEdit: (_id?: string) => void;
}

const MovieComponent: React.FC<MoviePropsExtended> = ({_id, producer, duration, title, dateOfRelease, seriesPart, isNotSaved,webViewPath, onEdit }) => (
    <IonItem color={isNotSaved ? "medium" : undefined} onClick={()=> onEdit(_id)}>
        <div className={styles.movieContainer}>
            <IonLabel className={styles.movieTitle}>
                <h1>{title}</h1>
            </IonLabel>
            <div className={styles.component}>
                <p>Producer: {producer} </p>
                <p>Duration: {duration}  min</p>
                {dateOfRelease && (
                    <p>Released at: {new Date(dateOfRelease).toDateString()} </p>
                )}
                {seriesPart && <p>Part of a serie: Yes</p>}
                <img src={webViewPath} alt={"No image"} width={'200px'} height={'200px'}/>
            </div>
        </div>
    </IonItem>
);

export default memo(MovieComponent);
