import { Card, CardContent, CardTitle } from "../components/ui/card";
const QuestionPart = ({img, question}) => {


    return(
        <Card  className="mt-5 w-full max-w-xs flex-col aspect-auto items-center justify-center p-6 bg-[#152B65] text-white rounded-2xl">
        <CardContent className="justify-center items-center text-center self-center">
            <div>{question}</div>
        </CardContent>
        </Card>
    )


}

export default QuestionPart