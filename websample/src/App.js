import './App.css';
import React, {Component} from 'react';
import EmojiReader from 'emoji-reader';

export default class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            text: "Input 😀 string with 👨‍👨‍👧‍👦",
            recognize: "",
            wrongLength: 0,
            correctLength: 0
        }
    }

    componentDidMount() {
        this._onTextChange(this.state.text)
    }

    render() {
        return (
            <div className="App">
                <label className="App-title">Emoji Reader</label>
                <div className="App-content">
                    <input className="App-input" type="text"
                           placeholder="Input a string with Emoji"
                           value={this.state.text}
                           onChange={(event) => this._onTextChange(event.target.value)}/>
                    <label className="App-Result">Recognize the emoji:
                        <label className="emphasize">  {this.state.recognize}</label></label>
                    <label className="App-Result">String.length:
                        <label className="emphasize">  {this.state.wrongLength}</label></label>
                    <label className="App-Result">EmojiReader.length:
                        <label className="emphasize">  {this.state.correctLength}</label></label>
                    <label className="App-Result">You can copy emoji from: <a target="view_window" href="https://getemoji.com/">https://getemoji.com/</a></label>
                    <label className="App-Result">Source Code: <a target="view_window" href="https://github.com/YvesCheung/EmojiReader">Github</a></label>
                </div>
            </div>
        )
    }

    _onTextChange(text) {
        let str = "";
        const itr = EmojiReader.analyzeText(text).iterator()
        while (itr.hasNext()) {
            const node = itr.next()
            if (node.isEmoji) {
                str += text.substr(node.startIndex, node.length)
            } else {
                str += "-"
            }
        }

        this.setState({
            text: text,
            recognize: str,
            wrongLength: str.length,
            correctLength: EmojiReader.getTextLength(text)
        })
    }
}
