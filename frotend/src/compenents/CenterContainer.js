import React, { Component } from "react"
import { Input, Comment, Avatar, Form,Button, List} from 'antd'
import moment from 'moment';
import Post from './Post'




const TextArea = Input.TextArea;

const CommentList = ({ comments }) => (
    <List
        dataSource={comments}
        header={`${comments.length} ${comments.length > 1 ? 'Post' : 'Posts'}`}
        itemLayout="horizontal"
        renderItem={props => <Post {...props} />}
    />
);

const Editor = ({onChange, onSubmit, submitting, value,}) => (
        <div>
            <Form.Item>
                <TextArea rows={3} onChange={onChange} value={value} />
            </Form.Item>
            <Form.Item>
                <Button
                    htmlType="submit"
                    loading={submitting}
                    onClick={onSubmit}
                    type="primary"
                >
                    Add Comment
                </Button>
            </Form.Item>
        </div>
);


export default class CenterContainer extends Component {
    constructor(props) {
        super(props)
        this.state = {
            comments: [],
            submitting: false,
            value: '',
        }
    }
    

    

    handleSubmit = () => {
        if (!this.state.value) {
            return;
        }

        this.setState({
            submitting: true,
        });

        setTimeout(() => {
            this.setState({
                submitting: false,
                value: '',
                comments: [
                    {
                        author: 'Han Solo',
                        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
                        content: <p>{this.state.value}</p>,
                        datetime: moment().fromNow(),
                    },
                    ...this.state.comments,
                ],
            });
        }, 500);
       // this.props.setAddComments()
        
    }

    handleChange = (e) => {
        this.setState({
            value: e.target.value,
        });
    }

    render() {
        const { comments, submitting, value } = this.state;
        return (
            <div className="container-fluid">
                <div className="row align-middle">
                    <div className="col-lg-8 ml-auto mr-auto align-bottom">
                        <Comment
                            avatar={(
                                <Avatar
                                    src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                                    alt="Han Solo"
                                />
                            )}
                            content={(
                                <Editor
                                    onChange={this.handleChange}
                                    onSubmit={this.handleSubmit}
                                    submitting={submitting}
                                    value={value}
                                />
                            )}
                        />
                        {comments.length > 0 && <CommentList comments={comments} />}
                    </div>
                </div>
            </div>
        )
    }

}


