import {
  OptionBase
} from "chakra-react-select";

interface LocationGroup extends OptionBase {
  label: string;
  value: string;
}

interface GenreGroup extends OptionBase {
  label: string;
  value: string;
}

interface RoleGroup extends OptionBase {
  label: string;
  value: string;
}

export type {  LocationGroup, GenreGroup, RoleGroup }