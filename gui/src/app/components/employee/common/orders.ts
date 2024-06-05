import { Rooms } from "../../admin/common/rooms";
import { Foods } from "../../client/common/foods";

export class Orders {

    id: number | undefined | null;

    roomEntity: Rooms | undefined | null;

    foodModelSet: Foods[] | undefined | null;

    states: string | undefined | null;

}
